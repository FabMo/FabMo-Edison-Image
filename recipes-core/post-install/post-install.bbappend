do_install_append() {
	awk '/systemctl stop blink-led/ { print "cp /etc/fstab /etc/fstab.backup; sed -r \"s:rootfs\\s+/\\s+auto\\s+:&ro,errors=remount-ro,:\" < /etc/fstab > /etc/fstab.ro; mv /etc/fstab.ro /etc/fstab\nsync\nsystemctl enable rootfs-overlay"}1' ${D}/sbin/post-install.sh > ${D}/sbin/post-install.sh.modified
	mv ${D}/sbin/post-install.sh.modified ${D}/sbin/post-install.sh
}
