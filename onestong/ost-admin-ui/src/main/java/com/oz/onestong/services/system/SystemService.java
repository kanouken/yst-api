package com.oz.onestong.services.system;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.oz.onestong.dao.system.AppVersionMapper;
import com.oz.onestong.dao.system.ibeacon.IbeaconInfoMapper;
import com.oz.onestong.model.department.Department;
import com.oz.onestong.model.system.ibeacon.IbeaconInfo;
import com.oz.onestong.model.system.ibeacon.IbeaconInfoExample;

@Service
public class SystemService {
	@Autowired
	private AppVersionMapper  appVersionMapper;
	
	@Transactional(readOnly = true)
	public Integer findAllVersionCount(){
		
		return this.appVersionMapper.countAll();
	}
		
		
		
	
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findAllVersions(){
		
		return this.appVersionMapper.selectAllVersions();
	}
		
		
		
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void updateVersion(Map<String, Object> version){
		this.appVersionMapper.updateVersionById(version);
		
	}
		
		
		


	
	
	/**
	 * 根据设备获取最新版本信息
	 * @param device
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> appVersionByDevice(String device) {
		Map<String, Object> version=  this.appVersionMapper.selectVersionByDevice(device);
		
		if(version != null){
			
			return version;
			
		}else{
			
			return null;
		}
		
	}
	private @Autowired IbeaconInfoMapper ibeaconInfoMapper;
	/**
	 * 绑定 ibeacon 
	 * @param bean
	 * @param dept
	 */
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void bindIbeaconWithDepartment(IbeaconInfo bean,Department dept){
		
		IbeaconInfo info  = this.findIbeaconInfo(dept.getDeptId());

		if(null != info){
			//update 
			info.setIbeaconUuid(bean.getIbeaconUuid());
			info.setIbeanconMajor(bean.getIbeanconMajor());
			info.setIbeanconMinor(bean.getIbeanconMinor());
			info.setLocationName(bean.getLocationName());
			info.setLongitude(bean.getLongitude());
			info.setLatitude(bean.getLatitude());
			info.setAddress(bean.getAddress());
			info.setUpdator(bean.getUpdator());
			info.setUpdateTime(bean.getCreateTime());
			this.ibeaconInfoMapper.updateByPrimaryKeySelective(info);
			
		}else{
			bean.setDepartmentId(dept.getDeptId());
			this.ibeaconInfoMapper.insertSelective(bean);
		}
		
		
	}
	
	@Transactional(readOnly = true)
	public IbeaconInfo findIbeaconInfo(Integer deptId){
		
		IbeaconInfoExample iie = new IbeaconInfoExample();
		iie.createCriteria().andDepartmentIdEqualTo(deptId);
		List<IbeaconInfo> infos =  this.ibeaconInfoMapper.selectByExample(iie);
		if(CollectionUtils.isNotEmpty(infos)){
			return infos.get(0);
			
			
		}
		
		return null;
	}
	
	
	
	@Transactional(readOnly = true)
	public List<IbeaconInfo> findAllIbeacon(){
		List<IbeaconInfo> infos =  this.ibeaconInfoMapper.selectAllSetUpIbeacons();
		return infos;
	}
		
}
