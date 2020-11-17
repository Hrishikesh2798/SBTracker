package com.ctlb.sbtracker

class Bus{
    var phn = ""
    var busno = ""
    var drvname = ""
    var type = "B"
    var long = 0
    var lat = 0
    var status = "I"

    constructor(phn : String,busno : String,drvname : String,long : Int,lat: Int,status: String)
    {
        this.phn = phn
        this.busno = busno
        this.drvname = drvname
        this.long = long
        this.lat = lat
        this.status = status
    }
}