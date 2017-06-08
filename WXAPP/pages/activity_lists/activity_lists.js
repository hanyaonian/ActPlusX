var app = getApp()
Page({
  data: {
    imgUrls: [
      '../../img/poster/pic1.jpg',
      '../../img/poster/pic3.jpg',
      '../../img/poster/pic4.png'
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    hotActivity: [
      {
        url: '../logs/logs',
        imgSrc: '../../img/poster/default.jpg',
        title: '2016中大招生宣传',
        type: '公益',
        time: '2017-9-8',
        position: '至九631'
      },
      {
        url: '../logs/logs',
        imgSrc: '../../img/poster/default.jpg',
        title: '2016中大招生宣传',
        type: '公益',
        time: '2017-9-8',
        position: '至九631'
      }
    ]
  },
  onLoad:function(event) {
      var that = this; 
      wx.request({
          url: 'https://actplus.sysuactivity.com/api/actlist?actType='+event.actType,
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
                  url: '../activity_details/activity_details',
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