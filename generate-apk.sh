#!/bin/bash

if [ "$TRAVIS_PULL_REQUEST" != "false" -o "$TRAVIS_REPO_SLUG" != "m-murad/keep" ]; then
    echo "Just a PR. Skip apk upload."
    exit 0
fi

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

# Unstage all the files in your working tree.
git rm --cached $(git ls-files)

# Copy newly generated apk(s) to current directory
cp $HOME/build/m-murad/keep/app/build/outputs/apk/debug/app-debug.apk $HOME/apk/
cp $HOME/build/m-murad/keep/app/build/outputs/apk/release/app-release-unsigned.apk $HOME/apk/

# Add and commit the files
git add -f app-debug.apk
git add -f app-release-unsigned.apk
git add .travis.yml
git commit --message "Apk(s) update for build:$TRAVIS_BUILD_NUMBER"

# Delete the branch name "apk"
git branch -D apk

# Rename the current branch from "temp" to "apk"
git branch -m apk

# Force push to the apk branch
git push origin apk --force --quiet> /dev/null
