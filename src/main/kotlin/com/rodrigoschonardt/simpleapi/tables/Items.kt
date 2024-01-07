package com.rodrigoschonardt.simpleapi.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object Items : UUIDTable ()
{
    val name = varchar( "name", 50 )
    val desc = text( "desc" )
}