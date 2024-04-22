<template>
  <div>
    <div class="container">
      <div style="padding: 10px">
        <!-- 搜索区域 -->
        <div style="margin: 10px 0">
          <el-input
            v-model="search"
            placeholder="请输入关键字"
            style="width: 200px"
            clearable
          ></el-input>
          <el-button
            type="primary"
            style="margin-left: 5px; margin-right: 10px"
            @click="init()"
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
          <el-table-column
            prop="userName"
            width="250"
            label="身份证"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="nickName" label="姓名" align="center">
          </el-table-column>
          <el-table-column
            prop="consultationContent"
            label="咨询内容"
            width="250"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="replyContent"
            width="250"
            label="回复内容"
            align="center"
          >
            <template #default="scope">
              <p>
                {{ scope.row.replyContent ? scope.row.replyContent : "未回复" }}
              </p>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            width="200"
            label="咨询时间"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="replayTime"
            width="200"
            label="回复时间"
            align="center"
          >
            <template #default="scope">
              <p>
                {{ scope.row.replayTime ? scope.row.replayTime : "未回复" }}
              </p>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150" align="center">
            <template #default="scope">
              <el-button
                v-if="!scope.row.replyContent"
                size="mini"
                type="success"
                @click="openDialog(scope.row.id)"
                plain
                >回复</el-button
              >
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
      </div>
    </div>
    <el-dialog
      title="回复"
      :visible.sync="dialogVisible"
      width="30%"
      @close="dialogVisible = false"
    >
      <el-form>
        <el-form-item label="回复内容" prop="replyContent">
          <el-input
            type="textarea"
            :rows="4"
            v-model="replyContent"
            placeholder="请输入回复内容"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitConsultation"
          >确认回复</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import moment from "moment";

export default {
  data() {
    return {
      loading: true,
      search: "",
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      dialogVisible: false,
      replyContent: "",
      id: "",
    };
  },
  methods: {
    openDialog(id) {
      this.dialogVisible = true;
      this.id = id;
      this.replyContent = "";
    },
    submitConsultation() {
      const data = {
        id: this.id,
        replyContent: this.replyContent,
      };
      request.post("/ask/doctorReply", data).then((res) => {
        if (res.code === "0") {
          this.$message({
            type: "success",
            message: "回复成功",
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
      this.dialogVisible = false;
    },
    init() {
      request
        .get("/ask/doctorPageList", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search,
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

    handleDelete(id) {
      request.post("/ask/remove", { id: id }).then((res) => {
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
</style>
