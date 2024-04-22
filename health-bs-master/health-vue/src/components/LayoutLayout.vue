<template>
  <!-- 页面布局 -->
  <div style="height: 100vh; overflow: hidden">
    <LayoutHeader class="layout-header" />
    <div style="display: flex">
      <LayoutAside />
      <router-view class="content-view"></router-view>
    </div>
  </div>
</template>

<script>
import LayoutHeader from "@/components/LayoutHeader";
import LayoutAside from "@/components/LayoutAside";

export default {
  name: "layoutLayout",
  components: {
    LayoutHeader,
    LayoutAside,
  },
};
</script>

<style>
/* 在这里可以添加 Layout 的样式 */
body {
  margin: 0;
}
.content-view {
  flex: 1;
  overflow-y: auto;
  background-color: #f5f6f7;
}
.layout-header {
  background-color: #ffffff;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 10000;
}
</style>
