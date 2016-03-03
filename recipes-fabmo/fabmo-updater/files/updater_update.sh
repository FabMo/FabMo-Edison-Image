#!/bin/sh 

echo "Stopping the updater..."
systemctl stop fabmo fabmo-updater

echo "Remounting root partition read-write"
mount -w -o remount /

# DANGER ZONE

echo "Resetting..."
cd /fabmo/updater
git reset --hard HEAD
echo "Fetching tags..."
git fetch origin --tags
echo "Checking out master..."
git checkout master
echo "Fetching master branch..."
git fetch
echo "Fetching release branches..."
git fetch origin release:release
git fetch origin rc:rc

echo "Updating to version $1..."
git checkout $1
sync

echo "Installing dependencies..."
npm install --production
sync

echo "Saving version information..."
set +e
git describe
INVALID_VERSION=$?
set -e
if [ $INVALID_VERSION -eq 0 ]; then
	VERSION=`git describe`
	echo "{\"number\" : \"$VERSION\" }" > /fabmo/updater/version.json
else
	rm /fabmo/updater/version.json || true
fi
sync

sleep 3
echo "Remounting root partition read only"
mount -r -o remount /

# END DANGER ZONE

echo "Restarting the updater..."
systemctl start fabmo fabmo-updater
