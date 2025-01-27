var http = require('http');
var express = require('express');
var app = express();

var server = http.createServer(app).listen(process.env.PORT || 5000);
app.get('/', function(req, res) {
  console.log('실행');
});


var io = require('socket.io').listen(server);
var mongoose = require('mongoose');
mongoose.connect('mongodb://ironfactory:12345678@ds019471.mlab.com:19471/heroku_g4p0fmhc');
var Schema = mongoose.Schema;
var UserSchema = Schema({
  'user_id' : String,
  'user_pw' : String,
  'user_device_id' : String,
  'user_accessable' : Number
});
var UserModel = mongoose.model('user', UserSchema);

io.on('connection', function(socket) {

  socket.on('signUp', function(data) {
    var id = data.id;
    var pw = data.pw;
    
    console.log('회원 추가 데이터 ');
    console.log('회원 추가 데이터 id = ' + id);
    console.log('회원 추가 데이터 pw = ' + pw);
    
    if (!id || !pw) {
      console.log('회원 추가 데이터 누락');
      socket.emit('signUp', {
        'code' : 300
      });
    } else {
      UserModel.find({'user_id' : id}, function(err, users) {
        if (err) {
          console.log('회원 추가 중복검사 DB쿼리 에러 = ' + err);
          socket.emit('signUp', {
            'code' : 302
          });
        } else {
          if (users[0] == null) {
            var user = new UserModel({
                'user_id' : id,
                'user_pw' : pw,
                'user_device_id' : null,
                'user_accessable' : 2
                });
            
            user.save(function(err) {
              if (err) {
                console.log('회원 추가 DB 저장 에러 = ' + err);
                socket.emit('signUp', {
                  'code' : 301
                });
              } else {
                console.log('회원 추가 성공');
                socket.emit('signUp', {
                  'code' : 200
                });
              }
            });
          } else {
            console.log('이미 해당 아이디 사용 중');
            socket.emit('signUp', {
              'code' : 302
            });
          }
        }
      });
    }
  });
  
  socket.on('setDeviceId', function(data) {
    var id = data.id;
    var deviceId = data.deviceId;

    console.log('setDeviceId');
    console.log('setDeviceId id = ' + id);
    console.log('setDeviceId deviceId = ' + deviceId);

    if (!id) {
      console.log('setDeviceId 데이터 누락');
      socket.emit('setDeviceId', {
        'code' : 430
      });
    } else {
      UserModel.update({'user_id' : id}, {'user_device_id' : deviceId}, function(err, user) {
        if (err) {
          console.log('로그인 DB쿼리 에러 = ' + err);
          socket.emit('setDeviceId', {
            'code' : 431
          });
        } else {
          socket.emit('setDeviceId', {
            'code' : 200,
            'user' : user
          });
        }
      });
    }
  });

  socket.on('login', function(data) {
    var id = data.id;
    var pw = data.pw;
    
    console.log('로그인 ');
    console.log('로그인 id = ' + id);
    console.log('로그인 pw = ' + pw);
    
    if (!id || !pw) {
      console.log('로그인 데이터 누락');
      socket.emit('login', {
        'code' : 330
      });
    } else {
      UserModel.find({'user_id' : id, 'user_pw' : pw}, function(err, user) {
        if (err) {
          console.log('로그인 DB쿼리 에러 = ' + err);
          socket.emit('login', {
            'code' : 331
          });
        } else {
          console.log('user = ' + user);
          if (user[0] != null) {
            console.log('로그인 성공.');
            socket.emit('login', {
              'code' : 200,
              'user' : user 
            });
          } else {
            console.log('로그인 실패 (계정 없음)');
            socket.emit('login', {
              'code' : 332
            });
          }
        }
      });
    }
  });
  
  socket.on('getUsers', function(data) {
    console.log('회원 리스트 로드');
    UserModel.find({}, function(err, users) {
      if (err) {
        console.log('회원 리스트 로드 실패');
        socket.emit('getUsers', {
          'code' : 311
        });
      } else {
        socket.emit('getUsers', {
          'code' : 200,
          'users' : users
        });
      }
    });
  });
  
  socket.on('setAccessable', function(data) {
    var id = data.id;
    var accessable = data.accessable;
    
    console.log('사용 권한 변경 ');
    console.log('사용 권한 변경 id = ' + id);
    console.log('사용 권한 변경 accessable = ' + accessable);
    
    if (!id || !accessable) {
      console.log('사용 권한 변경 데이터 누락'); 
      socket.emit('setAccessable', {
        'code' : 320
      });
    } else {
      UserModel.update({'user_id' : id}, {'user_accessable' : accessable}, function(err) {
        if (err) {
          console.log('사용 권한 변경 에러');
          socket.emit('setAccessable', {
            'code' : 321
          });
        } else {
          socket.emit('setAccessable', {
            'code' : 200
          });
        }
      });
    }
  });
});