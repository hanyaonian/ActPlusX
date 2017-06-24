//index.js
//获取应用实例
var app = getApp()
Page({
  // data: {
  //   motto: 'Hello World',
  //   userInfo: {}
  // },
  // //事件处理函数
  // bindViewTap: function() {
  //   wx.navigateTo({
  //     url: '../logs/logs'
  //   })
  // },
  // onLoad: function () {
  //   console.log('onLoad')
  //   var that = this
  //   //调用应用实例的方法获取全局数据
  //   app.getUserInfo(function(userInfo){
  //     //更新数据
  //     that.setData({
  //       userInfo:userInfo
  //     })
  //   })
  // }
  data: {
    action: [
      {
        url: '../logs/logs',
        imgSrc: '../../img/icon/team.png',
        name: '活动组队'
      },
      {
        url: '../logs/logs',
        imgSrc: '../../img/icon/issue.png',
        name: '发布活动'
      }
    ],
    activityType1: [
      {
        url: '../activity_lists/activity_lists?actType=welfare',
        imgSrc: '../../img/icon/publicGood.jpg',
        name: '公益'
      },
      {
        url: '../activity_lists/activity_lists?actType=game',
        imgSrc: '../../img/icon/competition.png',
        name: '比赛'
      },
      {
        url: '../activity_lists/activity_lists?actType=outside',
        imgSrc: '../../img/icon/outside.jpg',
        name: '户外'
      },
      {
        url: '../activity_lists/activity_lists?actType=lecture',
        imgSrc: '../../img/icon/lecture.jpg',
        name: '讲座'
      }
    ],
    activityType2: [
      {
        url: '../activity_lists/activity_lists?actType=casual',
        imgSrc: '../../img/icon/relaxation.png',
        name: '休闲'
      },
      {
        url: '../activity_lists/activity_lists?actType=performance',
        imgSrc: '../../img/icon/show.png',
        name: '演出'
      },
      {
        url: '../activity_lists/activity_lists?actType=PE',
        imgSrc: '../../img/icon/sports.jpg',
        name: '体育'
      },
      {
        url: '../activity_lists/activity_lists?actType=allList',
        imgSrc: '../../img/icon/all.png',
        name: '全部'
      }
    ],
    hotActivity: [
      {
        url: '../activity_details/activity_details',
        imgSrc: '../../img/icon/团队.jpg',
        title: '2016中大招生宣传',
        type: '公益',
        time: '2017-9-8',
        position: '至九631'
      },
      {
        url: '../activity_details/activity_details',
        imgSrc: '../../img/icon/团队.jpg',
        title: '2016中大招生宣传',
        type: '公益',
        time: '2017-9-8',
        position: '至九631'
      }
    ]
  },
  onLoad:function() {
      var that = this; 
      wx.request({
          url: 'https://actplus.sysuactivity.com/api/actlist',
          method: 'GET',
          success:function(res) {
              var data = res.data;
              console.log(data);
              var activity = [];
              for (var i = 0; i < data.length; i++) {
                var imgsrc = '';
                if (data[i].posterName == 'default.jpg') {
                  imgsrc = '../../img/poster/default.jpg';
                } else {
                  imgsrc = 'http://actplus.sysuactivity.com/imgBase/poster/'+data[i].posterName;
                }
                activity[i] = {
                  url: '../activity_details/activity_details?actId='+data[i].actId,
                  imgSrc: imgsrc,
                  title: data[i].actName,
                  type: data[i].actType,
                  time: data[i].actTime,
                  position: data[i].actLoc
                }
              }
              that.setData({
                hotActivity: activity
              })
          }

      })
  }
})
