package com.oz.onestong.dao.system.ibeacon;

import com.oz.onestong.model.system.ibeacon.IbeaconInfo;
import com.oz.onestong.model.system.ibeacon.IbeaconInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IbeaconInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    int countByExample(IbeaconInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    int deleteByExample(IbeaconInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    int deleteByPrimaryKey(Integer ibeaconId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    int insert(IbeaconInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    int insertSelective(IbeaconInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    List<IbeaconInfo> selectByExample(IbeaconInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    IbeaconInfo selectByPrimaryKey(Integer ibeaconId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    int updateByExampleSelective(@Param("record") IbeaconInfo record, @Param("example") IbeaconInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    int updateByExample(@Param("record") IbeaconInfo record, @Param("example") IbeaconInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    int updateByPrimaryKeySelective(IbeaconInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ibeacon_info
     *
     * @mbggenerated Wed May 06 14:35:20 CST 2015
     */
    int updateByPrimaryKey(IbeaconInfo record);
    
    List<IbeaconInfo> selectAllSetUpIbeacons();
    
    
}