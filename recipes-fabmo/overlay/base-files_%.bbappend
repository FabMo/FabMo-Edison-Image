do_install_append() {
    sed -r 's:(/dev/root.*)defaults:\1ro,errors=remount-ro:' < ${D}${sysconfdir}/fstab > ${D}${sysconfdir}/fstab
}
