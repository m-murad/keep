#!/bin/bash

# Travis build triggered on a forked repository
if [ "$TRAVIS_REPO_SLUG" != "m-murad/keep" ]; then
    echo "Not the original repo. Skip apk upload."
    exit 0
fi

# Travis build triggered by a PR
if [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
    echo "Just a PR. Skip apk upload."
    exit 0
fi

# Travis build triggered by a TAG
if [ -n "$TRAVIS_TAG"  ]; then
    echo "This is a TAG. Uploading apk to Google Play Store."
    # Decrypt the secret files
    cd $HOME/build/m-murad/keep/secrets
    openssl aes-256-cbc -K $encrypted_b9019075c133_key -iv $encrypted_b9019075c133_iv -in secrets.tar.enc -out secrets.tar -d
    tar xvf secrets.tar
    cd $HOME/build/m-murad/keep
    # Sign the apk using jarsigner
    jarsigner -verbose -tsa http://timestamp.comodoca.com/rfc3161 -sigalg SHA1withRSA -digestalg SHA1 -keystore secrets/keystore.jks -storepass $PASSWORD -keypass $KEY_PASSWORD app/build/outputs/apk/release/app-release-unsigned.apk $ALIAS
    # Align the apk using zipalign
    ${ANDROID_HOME}/build-tools/26.0.2/zipalign -vfp 4 app/build/outputs/apk/release/app-release-unsigned.apk app/build/outputs/apk/release/app-release-unsigned.apk
    # Install fastlane
    gem install fastlane
    # Publish the apk on Google Play Store
    fastlane supply --apk $HOME/build/m-murad/keep/app/build/outputs/apk/release/app-release-unsigned.apk --track rollout --rollout $TRAVIS_TAG --json_key secrets/google-api-key.json --package_name com.murad.jboss.keep
    exit 0
fi

# Travis build triggered by commit to a branch other than master
if [ "$TRAVIS_BRANCH" != "master" ]; then
    echo "Not pushed to master branch. Skip apk upload."
    exit 0
fi

# Travis build triggered by commit to master branch
if [ "$TRAVIS_BRANCH" == "master" ]; then
    echo "Pushed to master branch. Uploading apk(s) to apk branch"
    # Set git username
    git config --global user.name "m-murad"
    # Set git email
    git config --global user.email "murad.kuka@gmail.com"
    cd $HOME
    # Clone the "apk" branch of the repository
    git clone --quiet --branch=apk https://m-murad:$GITHUB_API_KEY@github.com/m-murad/keep  apk > /dev/null
    # cd to the cloned repository
    cd apk
    # Create an orphan branch with no initial commit
    git checkout --orphan temp
    # Delete the previous apk(s)
    rm -rf $HOME/apk/*
    # Unstage all the files in your working tree
    git rm --cached $(git ls-files)
    # Copy newly generated apk(s) to current directory
    cp $HOME/build/m-murad/keep/app/build/outputs/apk/debug/app-debug.apk $HOME/apk/
    cp $HOME/build/m-murad/keep/app/build/outputs/apk/release/app-release-unsigned.apk $HOME/apk/
    # Add and commit the files
    git add -f app-debug.apk
    git add -f app-release-unsigned.apk
    git add .travis.yml
    git commit --message "Apk(s) update for travis build:$TRAVIS_BUILD_NUMBER"
    # Delete the branch name "apk"
    git branch -D apk
    # Rename the current branch from "temp" to "apk"
    git branch -m apk
    # Force push to the apk branch
    git push origin apk --force --quiet> /dev/null
    # Upload the new debug apk to Appetize.io
    curl https://$APPETIZE_API_KEY@api.appetize.io/v1/apps/$APPETIZE_APP_KEY -H 'Content-Type: application/json' -d '{"url":"https://github.com/m-murad/keep/raw/apk/app-debug.apk"}' > /dev/null
fi
