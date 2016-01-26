do_install_append() {
   sed -r 's:rootfs\s+/\s+auto\s+:&ro,errors=remount-ro,:' < ${D}${sysconfdir}/fstab > ${D}${sysconfdir}/fstab.ro
   mv ${D}${sysconfdir}/fstab.ro ${D}${sysconfdir}/fstab
}
