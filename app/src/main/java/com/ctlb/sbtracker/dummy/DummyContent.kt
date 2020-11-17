package com.ctlb.sbtracker.dummy

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()
    val busnumbers: MutableList<String> = ArrayList()
    val phonenumbers: MutableList<String> = ArrayList()
    val drivernames: MutableList<String> =  ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()
    var count = 0
    var found = 0


    init {
        // Add some sample items.
    }

    fun dataUpdate()
    {
        val database = FirebaseDatabase.getInstance().reference
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                        Log.e("dummy","checking to clear lists $found")
                        //if(found != 0)
                        //{
                        Log.e("dummy","clearing list")
                        ITEMS.clear()
                        ITEM_MAP.clear()
                        busnumbers.clear()
                        phonenumbers.clear()
                        drivernames.clear()
                        count=0
                        //}
                        found = 1
                        for(i in p0.children)
                        {
                            if(i.child("type").getValue() == "B")
                            {

                                var bun = i.child("busno").getValue().toString()
                                var p =i.child("phn").getValue().toString()
                                Log.e("dummy","count $count bus no $bun phn $p")
                                busnumbers.add(i.child("busno").getValue().toString())
                                phonenumbers.add(i.child("phn").getValue().toString())
                                drivernames.add(i.child("drvname").getValue().toString())
                                count++
                            }
                        }
                        for (i in 0..count-1) {
                            Log.e("dummy","$i")
                            addItem(createDummyItem(phonenumbers.get(i),busnumbers.get(i), drivernames.get(i),i+1))
                        }

                    }

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
    }

    fun clearList()
    {
        busnumbers.clear()
        phonenumbers.clear()
        drivernames.clear()
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(phone: String, busno : String,drvname: String,count: Int): DummyItem {
        return DummyItem(busno.toString(), "Bus No:   " + busno, makeDetails(busno,phone,drvname,count))
    }

    private fun makeDetails(busno: String, phone:String,drvname: String,count: Int): String {
        val builder = StringBuilder()
        builder.append("\nDetails about Bus:").append(busno)
        for (i in 0..0) {
            builder.append("\nDriver name is $drvname")
            builder.append("\nPhone number for Bus is $phone.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content
    }
}