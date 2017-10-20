do_install_append() {
        echo "FabMo Engine Linux v1.8.2\\n" > ${D}${sysconfdir}/issue
        echo "FabMo Engine Linux v1.8.2\\n" > ${D}${sysconfdir}/issue.net
	echo "fabmo" > ${D}${sysconfdir}/hostname

    	install -d ${D}${sysconfdir}/systemd/network
	echo "[Match]\nName=en*\n[Network]\nDHCP=yes" > ${D}${sysconfdir}/systemd/network/ethernet-dhcp.network
	
    	lnr ${D}/run/systemd/network/resolv.conf ${D}/etc/resolv.conf
}
