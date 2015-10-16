do_install_append() {
        echo "FabMo Engine Linux\\n" > ${D}${sysconfdir}/issue
        echo "FabMo Engine Linux\\n" > ${D}${sysconfdir}/issue.net
	echo "fabmo" > ${D}${sysconfdir}/hostname
}
