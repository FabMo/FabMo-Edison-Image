do_install_append() {
    awk '/systemctl stop blink-led/ { print "/sbin/fabmo-post-install.sh"}1' ${D}/sbin/post-install.sh > ${D}/sbin/post-install.sh.modified
    mv ${D}/sbin/post-install.sh.modified ${D}/sbin/post-install.sh
}


