package com.rodrigoschonardt.simpleapi.data

import java.util.*

data class UpdateItemFormData(val uuid : UUID, val name : String, val desc : String )
{
    init {
        requireNotNull( uuid );
        require( name.length in 0..50 )
        require( desc.length in 0..400 )
    }
}
