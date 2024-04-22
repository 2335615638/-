<template>
  <div
    style="
      height: 50px;
      line-height: 50px;
      border-bottom: 1px solid #ccc;
      display: flex;
    "
  >
    <div
      style="
        width: 200px;
        padding-left: 30px;
        font-weight: bold;
        color: dodgerblue;
      "
    >
      xx管理系统
    </div>
    <div style="flex: 1"></div>

    <div style="width: 100px; font-weight: bold">
      <el-dropdown>
        <div class="user-info" style="display: flex">
          <img
            :src="userInfo.avatarUrl"
            class="el-dropdown-link headeravatar"
          />
          <div style="margin-left: 10px">{{ userInfo.nickName }}</div>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <div @click="openProfileDialog">
              <el-dropdown-item>个人信息</el-dropdown-item>
            </div>
            <div @click="openChangePasswordDialog">
              <el-dropdown-item>修改密码</el-dropdown-item>
            </div>
            <div @click="logout">
              <el-dropdown-item>退出系统</el-dropdown-item>
            </div>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

    <el-dialog
      :visible.sync="profileDialogVisible"
      width="600px"
      title="修改个人信息"
    >
      <el-form
        ref="profileForm"
        :model="profileForm"
        :rules="profileFormRules"
        label-width="150px"
        style="text-align: center"
      >
        <el-form-item label="头像" prop="avatar">
          <div
            style="
              background-color: #e9e9e9;
              width: 120px;
              height: 120px;
              box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            "
          >
            <el-upload
              class="avatar-uploader"
              action="/api/common/uploadImage?type=user-avatar"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
            >
              <img
                v-if="profileForm.avatarUrl"
                :src="profileForm.avatarUrl"
                style="width: 120px; height: 120px"
                class="avatar"
              />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="profileForm.userName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="profileForm.nickName"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group style="margin-left: -250px" v-model="profileForm.sex">
            <el-radio label="1" value="1">男</el-radio>
            <el-radio label="0" value="0">女</el-radio>
            <!-- Add other gender options as needed -->
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeProfileDialog">取消</el-button>
        <el-button type="primary" @click="updateProfile">确定</el-button>
      </div>
    </el-dialog>

    <!-- Change Password Dialog -->
    <el-dialog :visible.sync="passwordDialogVisible" title="修改密码">
      <el-form
        ref="passwordForm"
        :model="passwordForm"
        :rules="passwordFormRules"
        label-width="150px"
      >
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input
            type="password"
            v-model="passwordForm.oldPassword"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            type="password"
            v-model="passwordForm.newPassword"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input
            type="password"
            v-model="passwordForm.confirmPassword"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeChangePasswordDialog">取消</el-button>
        <el-button type="primary" @click="changePassword">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "LayoutHeader",
  data() {
    return {
      userInfo: {
        nickName: "admin",
        avatarUrl: "",
        sex: "",
        userName: "",
      },
      profileDialogVisible: false,
      passwordDialogVisible: false,
      profileForm: {
        userName: "",
        nickName: "",
        avatarUrl: "",
        sex: "1",
      },
      passwordForm: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: "",
      },
      profileFormRules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        nickName: [{ required: true, message: "请输入昵称", trigger: "blur" }],
        avatarUrl: [{ required: true, message: "请上传头像", trigger: "blur" }],
        sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
      },
      passwordFormRules: {
        oldPassword: [
          { required: true, message: "请输入旧密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" },
        ],
        confirmPassword: [
          { required: true, message: "请确认新密码", trigger: "blur" },
          { validator: this.validateConfirmPassword, trigger: "blur" },
        ],
      },
    };
  },
  created() {
    let userInfo = sessionStorage.getItem("userInfo");
    if (userInfo) {
      this.userInfo = JSON.parse(userInfo);
    } else {
      this.$router.push("/login");
    }
  },
  methods: {
    logout() {
      request.post("/user/logout");
      sessionStorage.removeItem("token");
      sessionStorage.removeItem("userInfo");
      this.$router.push("/login");
    },
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.passwordForm.newPassword) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    },

    openProfileDialog() {
      this.profileDialogVisible = true;
      this.profileForm = { ...this.userInfo };
    },
    closeProfileDialog() {
      this.profileDialogVisible = false;
      this.passwordForm = {
        oldPassword: "",
        newPassword: "",
        confirmPassword: "",
      };
    },
    updateProfile() {
      this.$refs.profileForm.validate((valid) => {
        if (valid) {
          //请求修改个人信息
          request.post("/user/updateInfo", this.profileForm).then((res) => {
            if (res.code === "0") {
              this.$message({
                type: "success",
                message: "更新成功",
              });
              sessionStorage.setItem(
                "userInfo",
                JSON.stringify(this.profileForm)
              );
              this.userInfo = this.profileForm;
              this.closeProfileDialog(); // 关闭弹窗
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

    openChangePasswordDialog() {
      this.passwordDialogVisible = true;
    },
    closeChangePasswordDialog() {
      this.passwordDialogVisible = false;
    },
    changePassword() {
      this.$refs.passwordForm.validate((valid) => {
        if (valid) {
          //请求修改密码
          request
            .post("/user/updatePassword", this.passwordForm)
            .then((res) => {
              if (res.code === "0") {
                this.$message({
                  type: "success",
                  message: "修改成功",
                });
                sessionStorage.removeItem("userInfo");
                sessionStorage.removeItem("token");
                this.closeChangePasswordDialog(); // 关闭弹窗
                this.$router.push("/login");
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
    handleAvatarSuccess(res) {
      this.profileForm.avatarUrl = res.data;
      console.log("上传成功", res);
      console.log("图片路径", this.profileForm.avatarUrl);
      this.$message.success("上传成功");
    },
  },
};
</script>

<style scoped>
a {
  text-decoration: none !important;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

/* 头像样式 */
.headeravatar {
  height: 30px;
  width: 30px;
  border-radius: 50%;
  border: 1px solid #bebebe;
  margin-top: 10px;
  transition: transform 0.3s ease; /* 添加过渡效果 */
}

/* 名字样式 */
.el-dropdown-link {
  transition: color 0.3s ease; /* 添加过渡效果 */
}

/* 下拉框菜单项悬停效果 */
.el-dropdown-menu__item:hover {
  background-color: #e6f7ff;
}

.el-form {
  margin-right: 50px;
}
</style>
