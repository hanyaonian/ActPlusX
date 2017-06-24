//index.js
//获取应用实例
var app = getApp()
Page({
    data: {
        bannerImgSrc: "../../img/icon/团队.jpg",
        banner: "带走中大眷恋",
        title: "寒招志愿者招募 | 中大5000+人报名的活动",
        details: [
            {
                imgSrc: "../../img/icon/团队.jpg",
                name: "活动时间",
                value: "5月5号"
            },
            {
                imgSrc: "../../img/icon/团队.jpg",
                name: "活动时间",
                value: "5月5号"
            },
            {
                imgSrc: "../../img/icon/团队.jpg",
                name: "活动时间",
                value: "5月5号"
            }
        ],
        organizerImgSrc: "../../img/icon/团队.jpg",
        organizerName: "中大招协",
        signedNum: "1234",
        pageViewNum: "5575",
        makeTeam: "我要组队",
        signUp: "我要报名",
        buttonMakeTeam: "../activity_enroll/activity_enroll",
        buttonSignUp: "../activity_enroll/activity_enroll"
    },
    onLoad:function(event) {
      var that = this; 
      wx.request({
          url: 'https://actplus.sysuactivity.com/api/actdetail?actId='+event.actId,
          method: 'GET',
          success:function(res) {
              var data = res.data;
              console.log(data);
              var details = [];
              var i = 0;
              if (data.actTime != '') {
                  details[i++] = {
                      imgSrc: "../../img/icon/time.png",
                      name: "活动时间",
                      value: data.actTime
                  }
              }
              if (data.actLoc != '') {
                  details[i++] = {
                      imgSrc: "../../img/icon/location.png",
                      name: "活动地点",
                      value: data.actLoc
                  }
              }
              if (data.actFor != '') {
                  details[i++] = {
                      imgSrc: "../../img/icon/actFor.png",
                      name: "活动对象",
                      value: data.actFor
                  }
              }
              if (data.actJoin != '') {
                  details[i++] = {
                      imgSrc: "../../img/icon/actJoin.png",
                      name: "报名方式",
                      value: data.actJoin
                  }
              }
              if (data.actDDL != '') {
                  details[i++] = {
                      imgSrc: "../../img/icon/actDDL.png",
                      name: "报名截止日期",
                      value: data.actDDL
                  }
              }
              if (data.welTime != '') {
                  details[i++] = {
                      imgSrc: "../../img/icon/welTime.png",
                      name: "公益时",
                      value: data.welTime
                  }
              }
              if (data.pechapter != '') {
                  details[i++] = {
                      imgSrc: "../../img/icon/welTime.png",
                      name: "体育章",
                      value: data.pechapter
                  }
              }
              if (data.otherAw != '') {
                  details[i++] = {
                      imgSrc: "../../img/icon/welTime.png",
                      name: "其他奖励",
                      value: data.otherAw
                  }
              }
              if (data.actIntru != '') {
                  details[i++] = {
                      imgSrc: "../../img/icon/actIntru.png",
                      name: "活动简介",
                      value: data.actIntru
                  }
              }
              if (data.actDetail != '') {
                  details[i++] = {
                      imgSrc: "../../img/icon/actIntru.png",
                      name: "活动详情",
                      value: data.actDetail
                  }
              }

            var imgsrc = '';
            if (data.posterName == 'default.jpg') {
                imgsrc = '../../img/poster/default.jpg';
            } else {
                imgsrc = 'http://actplus.sysuactivity.com/imgBase/poster/'+data.posterName;
            }
              that.setData({
                bannerImgSrc: imgsrc,
                banner: "带走中大眷恋",
                title: data.actName,
                details: details,
                qrSrc: 'http://actplus.sysuactivity.com/imgBase/qrImg/'+data.qrName
              })
          }

      });
      wx.request({
          url: 'https://actplus.sysuactivity.com/api/actmeta?actId='+event.actId,
          method: 'GET',
          success:function(res) {
              var data = res.data;
              console.log(data);
              that.setData({
                pageViewNum: data.pageView,
                signedNum: data.enrollPeoNum,
                buttonSignUp: "../activity_enroll/activity_enroll?actId="+event.actId
              })
          }

      })
  }
})
