package com.geishatokyo.sqlgen.process

import com.geishatokyo.sqlgen.core.{Sheet, Workbook}

/**
  * Created by takezoux2 on 2017/07/05.
  */
object WorkbookMerger {


  def merge(w1: Workbook, w2: Workbook) = {
    w2.sheets.foreach(s => {
      if(w1.hasSheet(s.name)) {
        mergeSheet(w1(s.name), s)
      } else {
        s.copyTo(w1)
      }
    })
    w2.note.foreach(t => {
      w1.addNote(t._1, t._2)
    })
    w2.metadatas.foreach(t => {
      w1.addMetadata(t._2)
    })

    w1
  }

  def mergeSheet(s1: Sheet, s2: Sheet) = {
    s1.addRows(s2.rows:_*)
  }

}
