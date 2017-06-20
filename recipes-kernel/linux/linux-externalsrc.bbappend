FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
COMPATIBLE_MACHINE = "edison"
LINUX_VERSION = "3.10.98"
SRCREV_machine = "b83b7a6fd37424078be4c98c454c25dd4c0355ad"
SRCREV_meta = "b83b7a6fd37424078be4c98c454c25dd4c0355ad"

SRC_URI += "file://usb-network.cfg"

PV = "${LINUX_VERSION}"
