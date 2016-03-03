do_install_append() {
        echo "FabMo Engine Linux v1.2.2\\n" > ${D}${sysconfdir}/issue
        echo "FabMo Engine Linux v1.2.2\\n" > ${D}${sysconfdir}/issue.net
	echo "fabmo" > ${D}${sysconfdir}/hostname
}
