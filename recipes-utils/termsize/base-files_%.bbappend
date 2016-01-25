do_install_append() {
    echo "/usr/bin/termsize" >> ${D}${sysconfdir}/profile
}
