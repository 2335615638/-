<template>
  <div class="homepage-hero-module">
    <div class="video-container">
      <div :style="fixStyle" class="filter">
        <div class="login-container">
          <div class="login-title">{{ logintitle }}</div>
          <el-form ref="form" :model="form" size="normal" :rules="rules">
            <el-form-item prop="userName">
              <el-input
                prefix-icon="el-icon-user-solid"
                v-model="form.userName"
                placeholder="请输入账号（用户为身份证号）"
              ></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                prefix-icon="el-icon-lock"
                v-model="form.password"
                show-password
                placeholder="请输入密码"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button style="width: 100%" type="primary" @click="login">{{
                buttonText
              }}</el-button>
            </el-form-item>
          </el-form>
          <button
            style="border: 0px; color: rgb(90, 90, 184)"
            @click="toggleMode"
          >
            {{ buttonToText }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  components: {},
  data() {
    return {
      logintitle: "欢迎登录",
      vedioCanPlay: false,
      fixStyle: "",
      form: {},
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
      isLoginMode: true,
      buttonText: "登录",
      buttonToText: "去注册",
    };
  },
  mounted() {
    sessionStorage.removeItem("user");
  },
  methods: {
    toggleMode() {
      this.isLoginMode = !this.isLoginMode;
      this.buttonText = this.isLoginMode ? "登录" : "注册";
      this.logintitle = this.isLoginMode ? "欢迎登录" : "欢迎注册";
      this.buttonToText = this.isLoginMode ? "去注册" : "去登录";
    },
    login() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const data = {
            userName: this.form.userName,
            password: this.form.password,
            userType: "1",
          };
          const url = this.isLoginMode ? "/user/login" : "/user/base/register";
          request.post(url, data).then((res) => {
            if (res.code === "0") {
              if (this.isLoginMode) {
                this.$message({
                  type: "success",
                  message: "登录成功",
                });
                sessionStorage.setItem("token", res.data.token);
                sessionStorage.setItem(
                  "userInfo",
                  JSON.stringify(res.data.user)
                );
                this.$router.push("/home");
              } else {
                this.$message({
                  type: "success",
                  message: "注册成功请登录",
                });
                window.location.reload();
              }
            } else {
              this.$message({
                type: "error",
                message: res.msg,
              });
            }
          });
        }
      });
    },
  },
};
</script>

<style scoped>
.homepage-hero-module,
.video-container {
  position: relative;
  height: 100vh;
  overflow: hidden;
  background: white;
}

.video-container .filter {
  z-index: 1;
  position: absolute;
  width: 100%;
}

.login-container {
  width: 400px;
  margin: 100px auto;
  background-color: #e5e5e5;
  padding: 50px;
  border-radius: 10px;
}

.login-title {
  font-size: 30px;
  text-align: center;
  padding: 30px 0;
  color: #333;
}

.fillWidth {
  width: 100%;
}
</style>
