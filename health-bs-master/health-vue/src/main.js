import Vue from 'vue'
import App from './App.vue'
import router from './router'; // 确保引入了路由
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

// 添加全局错误处理程序
const resizeObserverLoopErrorHandler = function (e) {
  console.error(e);
};
ResizeObserver.prototype.observe = function (target) {
  if (!target.__resizeObserverLoopErrorHandler__) {
    target.__resizeObserverLoopErrorHandler__ = resizeObserverLoopErrorHandler;
    window.addEventListener('error', target.__resizeObserverLoopErrorHandler__);
  }
};

Vue.use(ElementUI);
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

