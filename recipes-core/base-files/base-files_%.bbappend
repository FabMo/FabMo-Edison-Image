do_install_append() {
        echo "FabMo Engine Linux v1.0.4\\n" > ${D}${sysconfdir}/issue
        echo "FabMo Engine Linux v1.0.4\\n" > ${D}${sysconfdir}/issue.net
	echo "fabmo" > ${D}${sysconfdir}/hostname
}
