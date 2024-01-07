package com.rodrigoschonardt.simpleapi.controller

import com.rodrigoschonardt.simpleapi.service.ItemService
import com.rodrigoschonardt.simpleapi.data.AddItemFormData
import com.rodrigoschonardt.simpleapi.data.ItemDetailsData
import com.rodrigoschonardt.simpleapi.data.UpdateItemFormData
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*

@RestController
@RequestMapping( "/items" )
class ItemController( private val itemService : ItemService)
{
    @PostMapping
    fun create(@RequestBody body : AddItemFormData, uriBuilder : UriComponentsBuilder ) : ResponseEntity<String>
    {
        val uuid : UUID = itemService.create( body )

        val uri : URI = uriBuilder.path( "items/{uuid}" ).buildAndExpand( uuid ).toUri()

        return ResponseEntity.created( uri ).body( uuid.toString() )
    }

    @GetMapping( "/{uuid}" )
    fun fetchItem( @PathVariable uuid : UUID ) : ResponseEntity<ItemDetailsData>
    {
        val item : ItemDetailsData? = itemService.fetchById( uuid );

        return if ( item != null ) ResponseEntity.ok( item ) else ResponseEntity.noContent().build()
    }

    @GetMapping
    fun fetchAll() : ResponseEntity<List<ItemDetailsData>>
    {
        return ResponseEntity.ok( itemService.fetchAll() )
    }

    @PutMapping
    fun update( @RequestBody body : UpdateItemFormData ) : ResponseEntity<Unit>
    {
        itemService.update( body );

        return ResponseEntity.ok().build()
    }

    @DeleteMapping( "/{uuid}" )
    fun delete( @PathVariable uuid : UUID ) : ResponseEntity<Unit>
    {
        itemService.delete( uuid )

        return ResponseEntity.noContent().build()
    }
}