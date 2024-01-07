package com.rodrigoschonardt.simpleapi.data

data class AddItemFormData(val name : String, val desc : String )
{
    init {
        require( name.length in 0..50 )
        require( desc.length in 0..400 )
    }
}
