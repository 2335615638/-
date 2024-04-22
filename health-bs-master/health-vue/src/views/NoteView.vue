<template>
  <div>
    <div class="container">
      <div style="padding: 10px">
        <!-- 功能区域 -->
        <div style="margin: 10px 0">
          <el-button type="primary" @click="add">新增</el-button>
        </div>

        <div class="memo-card" v-for="(item, index) in tableData" :key="index">
          <div class="memo-header">
            <span class="memo-title">{{ item.title }}</span
            ><img :src="item.imgUrl" class="memo-image" />
          </div>
          <div class="memo-content">
            <div class="memo-text">{{ item.noteContent }}</div>
          </div>

          <div style="margin: 10px 0; text-align: right">
            <el-button
              size="mini"
              type="primary"
              plain
              @click="handleEdit(item)"
            >
              编辑
            </el-button>
            <el-button size="mini" @click="handleDelete(item.id)" type="danger"
              >删除</el-button
            >
          </div>
        </div>
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
            <el-form-item label="标题" prop="title">
              <el-input v-model="form.title" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="内容" prop="noteContent">
              <el-input
                type="textarea"
                v-model="form.noteContent"
                :rows="6"
                placeholder="请输入内容"
                maxlength="500"
                style="width: 80%; resize: none"
              ></el-input>
            </el-form-item>
            <el-form-item label="图片" prop="imgUrl">
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
                  action="/api/common/uploadImage?type=note-img"
                  :show-file-list="false"
                  :on-success="imgUrlUploadImgSuccess"
                >
                  <img
                    v-if="form.imgUrl"
                    :src="form.imgUrl"
                    style="width: 120px; height: 120px"
                    class="avatar"
                  />
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </div>
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
        userId: "",
        title: "",
        noteContent: "",
        imgUrl: "",
      },
      dialogVisible: false,
      currentPage: 1,
      pageSize: 10,
      dialogTitle: "添加",
      total: 0,
      tableData: [],
      categories: [],
      startTime: "",
      endTime: "",
      rules: {
        title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        noteContent: [
          { required: true, message: "内容不能为空", trigger: "blur" },
        ],
        imgUrl: [{ required: true, message: "图片不能为空", trigger: "blur" }],
      },
    };
  },
  methods: {
    init() {
      request
        .get("/note/notePageList", {
          params: {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
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
        userId: undefined,
        title: undefined,
        noteContent: undefined,
        imgUrl: undefined,
      };
      this.dialogVisible = true;
    },
    save() {
      request.post("/note/saveOrUpdateNote", this.form).then((res) => {
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
      request.post("/note/removeNote", { id: id }).then((res) => {
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
    imgUrlUploadImgSuccess(res) {
      this.form.imgUrl = res.data;
      this.$message.success("上传成功");
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

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.memo-card {
  border: 1px solid #e9e9e9;
  border-radius: 5px;
  margin-bottom: 20px;
  padding: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.memo-header {
  display: flex;
  align-items: center;
  margin-left: 14px;
}

.memo-title {
  font-size: 18px;
  font-weight: bold;
}

.memo-content {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.memo-image {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-left: 15px;
}

.memo-text {
  flex: 1;
  font-size: 14px;
  background-color: #f7f2f4;
  padding: 20px;
  border-radius: 5px;
}
</style>
