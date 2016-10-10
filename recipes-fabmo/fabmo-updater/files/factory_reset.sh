#!/bin/sh

echo "----------------------------------------------------------------"
echo "  FACTORY UPDATE.  DO NOT INTERRUPT POWER DURING THIS PROCESS."
echo "----------------------------------------------------------------"

echo "Stopping the factory reset monitor..."
systemctl stop factory-reset-monitor

if [ "$1" != "startup" ]; then
	echo "Stopping the engine and updater services..."
	systemctl stop fabmo fabmo-updater
fi

echo "Removing the contents of the user data directory..."
rm -rf /opt/fabmo/*

echo "Remounting root partition as read/write..."
mount -w -o remount /

#  DANGER ZONE
echo "Clearing installation..."
rm -rf /fabmo
mkdir -p /fabmo/updater
mkdir -p /fabmo/engine

echo "Decompressing factory updater..."
tar -xjf /usr/lib/fabmo/updater-factory.tar.bz2 -C /fabmo/updater
touch /fabmo/updater/install_token
#echo "Installing the updater service..."
#cp /fabmo/updater/files/fabmo-updater.service /etc/systemd/system
#systemctl daemon-reload
#systemctl enable fabmo-updater
echo "Synchronizing flash..."
sync

echo "Decompressing factory engine..."
tar -xjf /usr/lib/fabmo/engine-factory.tar.bz2 -C /fabmo/engine
touch /fabmo/engine/install_token
#echo "Installing the engine service..."
#cp /fabmo/engine/files/fabmo.service /etc/systemd/system
#systemctl daemon-reload
#systemctl enable fabmo

echo "Synchronizing flash..."
sync

# /DANGER ZONE
echo "Re-locking root partition..."
mount -r -o remount /

echo "Loading site files..."
mkdir -p /opt/fabmo/fmus
cp /usr/lib/fabmo/fmus/*.fmu /opt/fabmo/fmus

echo "Synchronizing flash..."
sync

systemctl start factory-reset-monitor
if [ "$1" != "startup" ]; then
	echo "Restarting the engine and updater services..."
	systemctl start fabmo fabmo-updater
fi
