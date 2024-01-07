package com.rodrigoschonardt.simpleapi.service

import com.rodrigoschonardt.simpleapi.data.AddItemFormData
import com.rodrigoschonardt.simpleapi.data.ItemDetailsData
import com.rodrigoschonardt.simpleapi.data.UpdateItemFormData
import com.rodrigoschonardt.simpleapi.tables.Items
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
@Transactional
class ItemService
{
    fun create( data : AddItemFormData ) : UUID
    {
        val uuid : EntityID<UUID> = Items.insertAndGetId{
            it[name] = data.name
            it[desc] = data.desc
        }

        return uuid.value;
    }

    fun fetchAll() : List<ItemDetailsData>
    {
        val items : List<ItemDetailsData> = Items.selectAll().map {
            ItemDetailsData( uuid = it[Items.id].value,
                             name = it[Items.name],
                             desc = it[Items.desc] ) }

        return items
    }

    fun fetchById( uuid : UUID ) : ItemDetailsData?
    {
        val item : ItemDetailsData? = Items.select( Items.id eq uuid ).firstOrNull()?.let {
            ItemDetailsData( uuid = it[Items.id].value,
                             name = it[Items.name],
                             desc = it[Items.desc] ) }

        return item
    }

    fun update( data : UpdateItemFormData )
    {
        Items.update( { Items.id eq data.uuid } ) {
            data.name.let { name -> it[Items.name] = name }
            data.desc.let { desc -> it[Items.desc] = desc } }
    }

    fun delete( uuid : UUID )
    {
        Items.deleteWhere { Items.id eq uuid }
    }
}