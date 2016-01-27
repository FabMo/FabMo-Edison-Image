#!/bin/sh
	
awk '/systemctl stop blink-led/ { print "sed -r \"s:rootfs\\s+/\\s+auto\\s+:&ro,errors=remount-ro,:\" < /etc/fstab > /tmp/fstab.ro; mv /tmp/fstab.ro /etc/fstab\nsystemctl enable rootfs-overlay"}1' post-install.sh
