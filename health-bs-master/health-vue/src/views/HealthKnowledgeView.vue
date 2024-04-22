<template>
  <div>
    <div class="container">
      <div style="padding: 10px">
        <!-- 搜索区域 -->
        <div style="margin: 10px 0">
          <el-button type="primary" @click="add">新增</el-button>
          <el-select
            v-model="typeSearch"
            placeholder="请选择"
            style="width: 200px; margin-left: 10px"
            clearable
          >
            <el-option label="就医篇" value="1"></el-option>
            <el-option label="误区篇" value="2"></el-option>
            <el-option label="饮食篇" value="3"></el-option>
            <el-option label="生活篇" value="4"></el-option>
          </el-select>
          <el-button
            type="primary"
            style="margin-left: 10px; margin-right: 10px"
            @click="init"
            >查询</el-button
          >
        </div>

        <el-table
          v-loading="loading"
          :data="tableData"
          border
          stripe
          style="width: 100%"
        >
          <el-table-column type="index" label="序号" width="50" align="center">
          </el-table-column>
          <el-table-column prop="title" label="知识标题" align="center">
          </el-table-column>
          <el-table-column prop="typeStr" label="知识类型" align="center">
          </el-table-column>
          <el-table-column prop="content" label="知识内容" align="center">
            <template slot-scope="scope">
              <div class="ellipsis-text">{{ scope.row.content }}</div>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="400" align="center">
            <template #default="scope">
              <el-button
                size="mini"
                type="primary"
                plain
                @click="handleEdit(scope.row)"
                >编辑</el-button
              >
              <el-popconfirm
                title="确定删除吗？"
                style="margin-left: 10px"
                @confirm="handleDelete(scope.row.id)"
              >
                <template #reference>
                  <el-button size="mini" type="danger">删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>

        <div style="margin: 10px 0; text-align: right">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="prev, pager, next, sizes,total"
            :total="total"
          ></el-pagination>
        </div>

        <el-dialog
          :title="dialogTitle"
          :visible.sync="dialogVisible"
          width="50%"
        >
          <el-form :model="form" :rules="rules" label-width="120px">
            <el-form-item label="知识标题" prop="title">
              <el-input
                v-model="form.title"
                placeholder="请输入标题"
                style="width: 80%"
              ></el-input>
            </el-form-item>
            <el-form-item label="知识类型" prop="type">
              <el-select
                v-model="form.type"
                placeholder="请选择"
                style="width: 80%"
              >
                <el-option label="就医篇" value="1"></el-option>
                <el-option label="误区篇" value="2"></el-option>
                <el-option label="饮食篇" value="3"></el-option>
                <el-option label="生活篇" value="4"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="知识内容" prop="content">
              <el-input
                type="textarea"
                v-model="form.content"
                :rows="6"
                placeholder="请输入内容"
                maxlength="500"
                style="width: 80%; resize: none"
              ></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="save">确定</el-button>
            </span>
          </template>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import moment from "moment";

export default {
  data() {
    return {
      loading: true,
      form: {
        id: "",
        type: "",
        content: "",
        createTime: "",
        createBy: "",
      },
      dialogVisible: false,
      typeSearch: "",
      currentPage: 1,
      pageSize: 10,
      dialogTitle: "添加",
      total: 0,
      tableData: [],
      categories: [],
      startTime: "",
      endTime: "",
      rules: {
        type: [
          { required: true, message: "知识类型不能为空", trigger: "blur" },
        ],
        content: [
          { required: true, message: "知识内容不能为空", trigger: "blur" },
        ],
        title: [
          { required: true, message: "知识标题不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    init() {
      request
        .get("/healthKnowledge/pageList", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            type: this.typeSearch,
          },
        })
        .then((res) => {
          if (res.code === "0") {
            this.tableData = res.data.records;
            this.total = res.data.total;
            this.loading = false;
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
        });
    },
    add() {
      this.dialogTitle = "添加";
      this.form = {
        id: undefined,
        type: undefined,
        content: undefined,
      };
      this.dialogVisible = true;
    },
    save() {
      request.post("/healthKnowledge/saveOrUpdate", this.form).then((res) => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: this.dialogTitle === "添加" ? "添加成功" : "修改成功",
          });
          this.init();
          this.dialogVisible = false;
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      });
    },
    handleEdit(row) {
      this.dialogTitle = "修改";
      this.form = row;
      this.dialogVisible = true;
    },
    handleDelete(id) {
      request.post("/healthKnowledge/remove", { id: id }).then((res) => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "删除成功",
          });
          this.init();
        } else {
          this.$message({
            type: "error",
            message: res.msg,
          });
        }
      });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.init();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.init();
    },
  },
  created() {
    this.init();
  },
  mounted() {
    console.log("Component mounted");
    console.log(moment);
    //  moment(this.endTime).format("YYYY-MM-DD HH:mm:ss")
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

.ellipsis-text {
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 隐藏溢出部分 */
  text-overflow: ellipsis; /* 显示省略号 */
}
</style>
