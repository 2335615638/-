<template>
  <div>
    <div class="container">
      <div class="knowledge-links">
        <router-link to="/userKnowledge/detail?type=1" class="knowledge-link">
          <div class="knowledge-item" style="font-size: 32px">
            <img src="../assets/jiuyi.png" style="width: 50px" />
            <div class="knowledge-text">就医篇</div>
          </div>
        </router-link>

        <router-link to="/userKnowledge/detail?type=2" class="knowledge-link">
          <div class="knowledge-item" style="font-size: 32px">
            <img src="../assets/wuqu.png" style="width: 50px" />
            <div class="knowledge-text">误区篇</div>
          </div>
        </router-link>

        <router-link to="/userKnowledge/detail?type=3" class="knowledge-link">
          <div class="knowledge-item" style="font-size: 32px">
            <img src="../assets/yinshi.png" style="width: 50px" />
            <div class="knowledge-text">饮食篇</div>
          </div>
        </router-link>

        <router-link to="/userKnowledge/detail?type=4" class="knowledge-link">
          <div class="knowledge-item" style="font-size: 32px">
            <img src="../assets/shenghuo.png" style="width: 50px" />
            <div class="knowledge-text">生活篇</div>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  min-height: 730px;
  background-color: white;
  margin: 20px;
  padding: 50px;

  border-radius: 5px;
}

.knowledge-links {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  height: 100px;
}

.knowledge-link {
  width: 150px; /* 设置每个正方形的宽度 */
  margin: 10px; /* 设置间距 */
  text-align: -webkit-center;
  text-decoration: none;
}

.knowledge-item {
  width: 150px; /* 设置整个链接区域为正方形 */
  background-color: #95aeb5; /* 设置背景色 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 8px; /* 设置圆角 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 设置阴影效果 */
  height: 150px;
}

.knowledge-icon {
  font-size: 24px; /* 设置图标的字体大小 */
}

.knowledge-text {
  font-size: 20px;
  color: white; /* 设置文字颜色 */
  margin-top: 5px; /* 调整文字与图标的间距 */
}
</style>
