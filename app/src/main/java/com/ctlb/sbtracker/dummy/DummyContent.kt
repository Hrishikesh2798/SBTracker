package com.ctlb.sbtracker.dummy

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
    val busnumbers: MutableList<Int> = ArrayList()
    val phonenumbers: MutableList<String> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()
    var count = 0



    init {
        val database = FirebaseDatabase.getInstance().reference
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                        for(i in p0.children)
                        {
                            if(i.child("type").getValue() == "B")
                            {
                                count++
                                busnumbers.add(i.child("busno").getValue().toString().toInt())
                                phonenumbers.add(i.child("phn").getValue().toString())
                            }
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        // Add some sample items.
        for (i in 1..count) {
            addItem(createDummyItem(phonenumbers[i],busnumbers[i]))
        }
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(phone: String, busno : Int): DummyItem {
        return DummyItem(busno.toString(), "Bus No" + busno, makeDetails(busno,phone))
    }

    private fun makeDetails(busno: Int, phone:String): String {
        val builder = StringBuilder()
        builder.append("Details about Bus: ").append(busno)
        for (i in 0..busno - 1) {
            builder.append("\nMore details information here.")
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