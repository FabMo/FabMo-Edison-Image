#!/bin/sh

echo "----------------------------"
echo "Handibot Config Installation"
echo "----------------------------"


echo "save the updater config"
cp  /opt/fabmo/config/updater.json ./
echo "Removing old config files"
rm -rf /opt/fabmo/config/*
echo "Checking the file tree"
mkdir -p /opt/fabmo/config/
echo "Copying default config files for handibot"
cp /fabmo/engine/config/default/*.json /opt/fabmo/config/
echo "Restoring config file for the updater"
cp ./updater.json /opt/fabmo/config/
