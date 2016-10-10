#!/bin/sh

echo "-----------------------"
echo "FabMo Post-Installation"
echo "-----------------------"

echo "Making a backup of the original fstab"
cp /etc/fstab /etc/fstab.backup

echo "Fixing /root link bug for npm"
ln -s /home/root /root

echo "Marking the rootfs as READ ONLY"
sed -r "s:rootfs\\s+/\\s+auto\\s+:&ro,errors=remount-ro,:" < /etc/fstab > /etc/fstab.ro; mv /etc/fstab.ro /etc/fstab

echo "Enable the rootfs RAM overlay service"
systemctl enable rootfs-overlay

echo "Create site-specific installation files"
mkdir -p /opt/fabmo/fmus
cp /usr/lib/fabmo/fmus/*.fmu /opt/fabmo/fmus


# Synchronize filesystems
echo "Synchronizing filesystems"
sync

echo "Restart the updater"
systemctl restart fabmo-updater
