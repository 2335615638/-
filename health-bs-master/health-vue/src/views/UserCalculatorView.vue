<template>
  <div>
    <div class="container">
      <div style="width: 100%; text-align: center; margin-bottom: 48px">
        <h1>体脂率（BFR）计算器</h1>
      </div>
      <el-form :model="form" :rules="rules" label-width="120px" ref="form">
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择" style="width: 80%">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="体重kg" prop="weight">
          <el-input
            v-model="form.weight"
            style="width: 80%"
            :maxlength="3"
            :max="300"
            :min="0"
          ></el-input>
        </el-form-item>
        <el-form-item label="身高cm" prop="height">
          <el-input
            v-model="form.height"
            style="width: 80%"
            :maxlength="3"
            :max="300"
            :min="0"
          ></el-input>
        </el-form-item>
        <el-form-item label="年龄（周岁）" prop="age">
          <el-input
            v-model="form.age"
            style="width: 80%"
            :maxlength="3"
            :max="150"
            :min="0"
          ></el-input>
        </el-form-item>
      </el-form>

      <div style="width: 100%; text-align: center">
        <el-button type="primary" @click="search">查询</el-button>
      </div>
      <div
        style="
          width: 100%;
          text-align: center;
          margin-top: 48px;
          margin-bottom: 48px;
        "
      >
        <div>
          <p v-if="bodyFatPercent">您的体脂率(BFR)为：{{ bodyFatPercent }}%</p>
        </div>
      </div>
      <h3>介绍</h3>
      <p style="margin-left: 20px; margin-top: 10px">
        在线体脂率BFR计算器，可以通过BMI公式根据您的身高体重，年龄及性别快速计算出您的体脂率，随时了解您的身体健康状况;成年人的体脂率正常范围分别是女性20%～25%，男性15%～18%，若体脂率过高，体重超过正常值的20%以上就可视为肥胖。运动员的体脂率可随运动项目而定。一般男运动员为7%～15%，女运动员为12%—20%。
      </p>
      <h3 style="margin-top: 10px">计算方法</h3>
      <div>
        <p style="margin-top: 10px"><strong>BMI算法计算：</strong></p>
        <p style="margin-left: 20px; margin-top: 10px">
          （1）BMI=体重（公斤）÷（身高×身高）（米）。
        </p>
        <p style="margin-left: 20px; margin-top: 10px">
          （2）体脂率：1.2×BMI+0.23×年龄-5.4-10.8×性别（男为1，女为0）。
        </p>
      </div>
      <div>
        <p style="margin-top: 10px">另一种体脂率算法：</p>
        <p style="margin-left: 20px; margin-top: 10px">
          成年女性：<br />
          参数a=腰围（cm）×0.74 <br />参数b=体重（kg）×0.082+34.89
          <br />体脂肪重量（kg）=a－b
          <br />体脂率=（身体脂肪总重量÷体重）×100%<br />
          成年男性的：<br />
          参数a=腰围（cm）×0.74 <br />参数b=体重（kg）×0.082+44.74<br />
          体脂肪重量（kg）=a－b 体脂率=（身体脂肪总重量÷体重）×100%。
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      bodyFatPercent: "",
      form: {
        id: "",
        sex: "",
        weight: "",
        height: "",
        age: "",
      },
      rules: {
        sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
        weight: [
          { required: true, message: "请输入体重", trigger: "blur" },
          { validator: this.validatePositiveInteger, trigger: "blur" },
          { validator: this.validateMaxValue, trigger: "blur" },
        ],
        height: [
          { required: true, message: "请输入身高", trigger: "blur" },
          { validator: this.validatePositiveInteger, trigger: "blur" },
          { validator: this.validateMaxValue, trigger: "blur" },
        ],
        age: [
          { required: true, message: "请输入年龄", trigger: "blur" },
          { validator: this.validatePositiveInteger, trigger: "blur" },
          { validator: this.validateMaxValue, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    validatePositiveInteger(rule, value, callback) {
      const reg = /^\d+$/;
      if (value && !reg.test(value)) {
        callback(new Error("请输入正整数"));
      } else {
        callback();
      }
    },
    validateMaxValue(rule, value, callback) {
      const max = rule.field === "weight" ? 500 : 300; // Adjust max value based on the field
      if (value && parseInt(value) > max) {
        callback(new Error("输入值超过最大限制"));
      } else {
        callback();
      }
    },
    search() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          request.post("/calculator/search", this.form).then((res) => {
            if (res.code === "0") {
              this.bodyFatPercent = res.data;
              this.dialogVisible = false;
            } else {
              this.$message({
                type: "error",
                message: res.msg,
              });
            }
          });
        } else {
          this.$message.error("表单验证未通过，请检查输入！");
        }
      });
    },
  },
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
  padding-bottom: 100px;
}
</style>
