package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("insert into FILES(filename,contenttype,filesize,userid,filedata) values(#{fileName}, #{contentType}, #{fileSize}, #{userid}, #{fileData})")
    @Options(useGeneratedKeys = true,keyProperty = "fileid")
    int addFile(File file);

    @Select("select * from FILES where fileid=#{fileid} ")
    File findFileById(Integer fileid); //要用fileid来查找 因为不同user可以存下相同的文件名的文件！！

    @Select("select * from Files where filename = #{fileName}")
    User findByName(String fileName);

    @Select("select * from FILES where userid=#{userid}")
    List<File> findAllFiles(Integer userid);

    @Delete("delete from FILES where fileid=#{fileid}")
    int deleteById(Integer fileid);
}
