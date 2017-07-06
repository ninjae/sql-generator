package com.geishatokyo.sqlgen.meta

import java.io.{InputStream, InputStreamReader}

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._

/**
  * Created by takezoux2 on 2017/07/05.
  */
class TypeSafeConfigMetaLoader extends MetaLoader{
  override def load(input: InputStream): Metadata = {
    try {
      val conf = ConfigFactory.parseReader(new InputStreamReader(input, "utf-8"))
      loadConfig(conf)
    }finally{
      input.close()
    }
  }

  val SheetProp = "sheets"
  val NameProp = "name"
  val ColumnProp = "columns"


  def loadConfig(config: Config): Metadata = {


    if(config.hasPath(SheetProp)) {
      Metadata(config.getConfigList(SheetProp).asScala.map(conf => {
        loadSheet(conf)
      }).toList)
    } else {
      Metadata(Nil)
    }


  }

  private def loadSheet(config: Config): SheetMeta = {

    val name = config.getString(NameProp)
    if(config.hasPath(ColumnProp)) {
      val cols = config.getConfigList(ColumnProp).asScala.map(loadColumn(_)).toList
      SheetMeta(name, cols)
    } else {
      SheetMeta(name, Nil)
    }

  }
  private def loadColumn(config: Config): ColumnMeta = {

    val name = config.getString(NameProp)

    ColumnMeta(name)
  }

}