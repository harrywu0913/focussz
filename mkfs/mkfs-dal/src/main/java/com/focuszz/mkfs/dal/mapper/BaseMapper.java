package com.focuszz.mkfs.dal.mapper;

public interface BaseMapper {

    public <T> int insert(T t);

    public <T> T queryById(int id);

    public <T> void deleteById(int id);

    public <T> void update(T t);

}
