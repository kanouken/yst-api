package org.ost.entity.notification;


	
public class Message {

		private  Long id;
		private  Long houseadviserId ;
		
		private  Long groupId;
		
		private  int type;
	
		private  String title;
		
		private  String content;
		
		private Long  couponTypeId;

		
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getHouseadviserId() {
			return houseadviserId;
		}

		public void setHouseadviserId(Long houseadviserId) {
			this.houseadviserId = houseadviserId;
		}

		public Long getGroupId() {
			return groupId;
		}

		public void setGroupId(Long groupId) {
			this.groupId = groupId;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public Long getCouponTypeId() {
			return couponTypeId;
		}

		public void setCouponTypeId(Long couponTypeId) {
			this.couponTypeId = couponTypeId;
		}
		
		
	}

	

