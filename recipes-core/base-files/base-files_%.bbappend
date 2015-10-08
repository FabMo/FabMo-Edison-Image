do_install_append() {
        echo "FabMo Engine Linux\\n\\s\\n\\m\\n\\r\\n" > ${D}${sysconfdir}/issue
        echo "FabMo Engine Linux\\n\\s\\n\\m\\n\\r\\n" > ${D}${sysconfdir}/issue.net
	echo "fabmo" > ${D}${sysconfdir}/hostname
}
