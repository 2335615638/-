<template>
  <div>
    <div class="container">
      <div style="width: 100%; text-align: center; margin-bottom: 48px">
        <h1>{{ title }}</h1>
      </div>
      <div class="health-tips" v-for="(item, index) in dataList" :key="index">
        <div class="tip">
          <p style="font-size: 20px; font-weight: 600">
            {{ index + 1 }}.{{ item.title }}
          </p>
          <p
            style="
              margin-left: 40px;
              marin-top: 10px;
              margin-left: 40px;
              margin-top: 10px;
            "
          >
            {{ item.content }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      message: "Hello, Vue!",
      title: "",
      dataList: [],
    };
  },
  methods: {
    init() {
      request
        .get("/healthKnowledge/getListByType", {
          params: {
            type: this.$route.query.type,
          },
        })
        .then((res) => {
          if (res.code === "0") {
            this.dataList = res.data;
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
        });
    },
  },
  created() {
    const typeMap = {
      1: "就医篇",
      2: "误区篇",
      3: "饮食篇",
      4: "生活篇",
    };
    this.title = typeMap[this.$route.query.type];
    this.init();
  },
  mounted() {
    console.log("Component mounted");
  },
};
</script>

<style scoped>
.container {
  min-height: 730px;
  background-color: white;
  margin: 20px;
  padding: 50px;

  border-radius: 5px;
}

.health-tips {
  max-width: 600px;
  margin: 0 auto;
  font-family: "Arial", sans-serif;
}

.tip {
  font-size: 16px;
  line-height: 1.5;
  color: #333;
  margin-bottom: 20px;
}
</style>
