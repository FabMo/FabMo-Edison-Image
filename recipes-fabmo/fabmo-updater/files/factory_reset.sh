#!/bin/sh

echo "----------------------------------------------------------------"
echo "  FACTORY UPDATE.  DO NOT INTERRUPT POWER DURING THIS PROCESS."
echo "----------------------------------------------------------------"

echo "Stopping the engine and updater services..."
systemctl stop fabmo fabmo-updater factory-reset-monitor

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
echo "Installing the updater service..."
cp /fabmo/updater/files/fabmo-updater.service /etc/systemd/system

echo "Synchronizing flash..."
sync

echo "Decompressing factory engine..."
tar -xjf /usr/lib/fabmo/engine-factory.tar.bz2 -C /fabmo/engine
echo "Installing the engine service..."
cp /fabmo/engine/files/fabmo.service /etc/systemd/system

## Handibot Specific section

echo "Making macros tree"
mkdir -p /opt/fabmo/macros/
echo "Copying default macros for handibot"
cp \
/fabmo/engine/config/default/macros/macro_7.sbp \
/fabmo/engine/config/default/macros/macro_9.sbp \
/fabmo/engine/config/default/macros/macro_2.sbp \
/fabmo/engine/config/default/macros/macro_77.sbp \
/fabmo/engine/config/default/macros/macro_90.sbp \
/fabmo/engine/config/default/macros/macro_78.sbp \
/fabmo/engine/config/default/macros/macro_91.sbp \
/fabmo/engine/config/default/macros/macro_3.sbp \
/fabmo/engine/config/default/macros/macro_6.sbp \
/fabmo/engine/config/default/macros/macro_79.sbp \
/opt/fabmo/macros/ || echo "error on the last task : "$?


echo "Making the config tree"
mkdir -p /opt/fabmo/config/
echo "Coping default config for handibot"
cp /fabmo/engine/config/default/*.json /opt/fabmo/config/ 

## /Handibot Specific section

echo "launching the services ..."
systemctl daemon-reload
systemctl enable fabmo-updater
systemctl enable fabmo
# /DANGER ZONE
echo "Synchronizing flash..."
sync
sleep 3

echo "Re-locking root partition..."
mount -r -o remount /

echo "Restarting services..."
systemctl start fabmo fabmo-updater factory-reset-monitor
