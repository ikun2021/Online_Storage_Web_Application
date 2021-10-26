package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("select * from NOTES where userid=#{userid}")
    List<Note> findAllNotes(Integer userid);

    @Insert("insert into NOTES(notetitle,notedescription,userid) values(#{noteTitle},#{noteDescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    void addNote(Note note);

    @Update("UPDATE notes SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteid} ")
    public void updateNote(Note note);

    @Delete("delete from NOTES where noteid=#{noteid}")
    void deleteById(Integer noteid);
}
