// pages/activity_enroll/activity_enroll.js
Page({
  data:{
    enrollSrc: "../../img/icon/time.png",
    enrollInfo: [
      {
        imgSrc: "../../img/icon/time.png",
        name: "姓名",
        inputName: "partName",
        placeholder: "请输入姓名"
      },
      {
        imgSrc: "../../img/icon/time.png",
        name: "学号",
        inputName: "stuId",
        placeholder: "请输入学号"
      },
      {
        imgSrc: "../../img/icon/time.png",
        name: "邮箱",
        inputName: "email",
        placeholder: "请输入邮箱"
      },
      {
        imgSrc: "../../img/icon/time.png",
        name: "院系",
        inputName: "academy",
        placeholder: "请输入院系"
      },
      {
        imgSrc: "../../img/icon/time.png",
        name: "联系信息",
        inputName: "phoneNum",
        placeholder: "请输入电话"
      }
    ]
  },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
    this.setData({
      actId: parseInt(options.actId)
    })
  },
  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示
  },
  onHide:function(){
    // 页面隐藏
  },
  onUnload:function(){
    // 页面关闭
  },
  formSubmit:function(e) {
    var that = this;
    var formData = e.detail.value;
    formData.actId=this.data.actId;
    wx.request({
      url: 'https://actplus.sysuactivity.com/api/enroll/newEnroll',
      data: formData,
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {
        "Content-Type": "application/x-www-form-urlencoded" 
      }, // 设置请求的 header
      success: function(res){
        // success
        var data = res.data;
        console.log(formData);
        console.log(data);
      },
      fail: function() {
        // fail
      },
      complete: function() {
        // complete
      }
    })
  }
})