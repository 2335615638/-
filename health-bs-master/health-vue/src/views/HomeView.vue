<template>
  <div>
    <div class="container"><h1>首页</h1></div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      message: "Hello, Vue!",
    };
  },
  methods: {},
  created() {
    console.log("Component created");
    console.log(request);
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
</style>
