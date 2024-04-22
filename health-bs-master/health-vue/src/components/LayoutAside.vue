<template>
  <div>
    <el-menu
      style="height: calc(100vh - 50px); width: 200px"
      class="el-menu-vertical-demo"
      :default-active="currentMenuIndex"
      active-text-color="#ffd04b"
      background-color="#545c64"
      text-color="#fff"
    >
      <!-- 使用 v-for 动态生成菜单项和子菜单项 -->
      <router-link to="/home">
        <el-menu-item index="/home">
          <el-icon class="el-icon-s-home"></el-icon>
          <span class="menu-text">首页</span>
        </el-menu-item>
      </router-link>

      <el-submenu index="2" v-if="userInfo.role === 'ADMIN'">
        <template slot="title">
          <el-icon class="el-icon-setting"></el-icon>
          <span class="menu-text">系统管理</span>
        </template>

        <router-link to="/user">
          <el-menu-item index="/user" class="submenu-item">
            <el-icon class="el-icon-user"></el-icon>
            <span class="menu-text">用户管理</span>
          </el-menu-item>
        </router-link>
      </el-submenu>

      <router-link to="/knowledge" v-if="userInfo.role === 'ADMIN'">
        <el-menu-item index="/knowledge" class="submenu-item">
          <el-icon class="el-icon-reading"></el-icon>
          <span class="menu-text">健康知识管理</span>
        </el-menu-item>
      </router-link>

      <router-link to="/calculator" v-if="userInfo.role === 'ADMIN'">
        <el-menu-item index="/calculator" class="submenu-item">
          <el-icon class="el-icon-files"></el-icon>
          <span class="menu-text">体脂率记录</span>
        </el-menu-item>
      </router-link>

      <router-link to="/healthRecords" v-if="userInfo.role === 'DOCTOR'">
        <el-menu-item index="/healthRecords" class="submenu-item">
          <el-icon class="el-icon-s-opportunity"></el-icon>
          <span class="menu-text">健康档案</span>
        </el-menu-item>
      </router-link>

      <router-link to="/doctor/ask" v-if="userInfo.role === 'DOCTOR'">
        <el-menu-item index="/doctor/ask" class="submenu-item">
          <el-icon class="el-icon-phone"></el-icon>
          <span class="menu-text">用户回复</span>
        </el-menu-item>
      </router-link>

      <router-link to="/userKnowledge" v-if="userInfo.role === 'USER'">
        <el-menu-item index="/userKnowledge" class="submenu-item">
          <el-icon class="el-icon-s-opportunity"></el-icon>
          <span class="menu-text">健康知识</span>
        </el-menu-item>
      </router-link>

      <router-link to="/userCalculator" v-if="userInfo.role === 'USER'">
        <el-menu-item index="/userCalculator" class="submenu-item">
          <el-icon class="el-icon-date"></el-icon>
          <span class="menu-text">体脂率计算器</span>
        </el-menu-item>
      </router-link>

      <router-link to="/userHealthMeasurements" v-if="userInfo.role === 'USER'">
        <el-menu-item index="/userHealthMeasurements" class="submenu-item">
          <el-icon class="el-icon-s-order"></el-icon>
          <span class="menu-text">健康记录</span>
        </el-menu-item>
      </router-link>

      <router-link to="/physicalFunctions" v-if="userInfo.role === 'USER'">
        <el-menu-item index="/physicalFunctions" class="submenu-item">
          <el-icon class="el-icon-s-custom"></el-icon>
          <span class="menu-text">身体机能</span>
        </el-menu-item>
      </router-link>

      <router-link
        :to="'/healthRecords/statistic?userId=' + userInfo.id + '&type=user'"
        v-if="userInfo.role === 'USER'"
      >
        <el-menu-item index="/healthRecords" class="submenu-item">
          <el-icon class="el-icon-s-data"></el-icon>
          <span class="menu-text">个人统计</span>
        </el-menu-item>
      </router-link>

      <router-link to="/note" v-if="userInfo.role === 'USER'">
        <el-menu-item index="/note" class="submenu-item">
          <el-icon class="el-icon-notebook-1"></el-icon>
          <span class="menu-text">备忘录</span>
        </el-menu-item>
      </router-link>

      <router-link to="/userDoctor" v-if="userInfo.role === 'USER'">
        <el-menu-item index="/userDoctor" class="submenu-item">
          <el-icon class="el-icon-phone"></el-icon>
          <span class="menu-text">医生咨询</span>
        </el-menu-item>
      </router-link>
    </el-menu>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "LayoutAside",
  data() {
    return {
      // 定义菜单数据
      currentMenuIndex: this.getPathMapping(),
      userInfo: {},
    };
  },
  computed: {},
  created() {
    let userInfo = sessionStorage.getItem("userInfo");
    if (userInfo) {
      this.userInfo = JSON.parse(userInfo);
    }
    // 监听路由变化，更新currentMenuIndex
    this.$watch("$route", () => {
      this.currentMenuIndex = this.getPathMapping();
    });
  },
  methods: {
    getPathMapping() {
      const pathMap = {
        "/": "/home",
        "/home": "/home",
        "/user": "/user",
        "/knowledge": "/knowledge",
        "/userKnowledge": "/userKnowledge",
        "/userKnowledge/detail": "/userKnowledge",
        "/userCalculator": "/userCalculator",
        "/calculator": "/calculator",
        "/healthRecords/statistic": "/healthRecords",
        "/healthRecords": "/healthRecords",
        "/userHealthMeasurements": "/userHealthMeasurements",
        "/userHealthMeasurements/doctorSuggest": "/userHealthMeasurements",
        "/physicalFunctions": "/physicalFunctions",
        "/note": "/note",
        "/userDoctor": "/userDoctor",
        "/userDoctor/reply": "/userDoctor",
        "/doctor/ask": "/doctor/ask",
      };
      const currentPath = this.$route.path;
      return pathMap[currentPath] || currentPath;
    },
    init() {
      console.log(request);
    },
  },
};
</script>

<style scoped>
/* 样式调整 */
.el-menu-vertical-demo .el-submenu__title {
  display: flex;
  align-items: center;
}

.submenu-item {
  padding-left: 200px; /* 调整子菜单项的缩进 */
}

/* 鼠标悬浮样式 */
.el-menu-vertical-demo .el-submenu.is-opened,
.el-menu-vertical-demo .el-submenu.is-active {
  background-color: #3a3f44;
}

.el-menu-vertical-demo .el-submenu__title:hover {
  background-color: #3a3f44;
}

.el-menu-vertical-demo .el-menu-item:hover {
  background-color: #3a3f44;
}

.el-menu-vertical-demo .el-menu-item,
.el-menu-vertical-demo .el-submenu__title {
  transition: background-color 0.3s; /* Add a transition effect */
}

/* 鼠标悬浮样式 */
.el-menu-vertical-demo .el-submenu.is-opened,
.el-menu-vertical-demo .el-submenu.is-active,
.el-menu-vertical-demo .el-submenu__title:hover,
.el-menu-vertical-demo .el-menu-item:hover {
  background-color: #3a3f44; /* Apply the background color on hover */
}

.router-link-exact-active,
.router-link-active {
  text-decoration: none !important;
}
a {
  text-decoration: none !important;
}
</style>
