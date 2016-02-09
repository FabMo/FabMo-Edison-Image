do_install_append() {
	rm ${D}${sysconfdir}/systemd/network/usb0.network
}
