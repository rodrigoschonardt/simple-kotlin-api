package com.rodrigoschonardt.simpleapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleapiApplication

fun main( args : Array<String> )
{
	runApplication<SimpleapiApplication>( *args )
}
