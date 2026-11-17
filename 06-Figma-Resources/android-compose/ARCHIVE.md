# 归档说明（2026-11-18）

本目录是 **M3 Kit / android-compose** 示例的归档副本，原工作区仍在：

`D:\AI项目\test\android-compose`

## 内容

- `app/`：可运行的 Jetpack Compose 示例 App；
- `components/`、`ui/`：手写组件与尺寸 token；
- `screenshots/`：真机截图；
- `pixel-baseline/`、`pixel-baseline-p1p2/`：人工 Pixel 基线；
- `dist/m3-kit-debug.apk`：已构建的 debug APK；
- `*.md`：组件试点、回归、参数缺口与决策记录。

## 构建

项目使用 material3 1.5.0-rc01，离线构建参数见 README；构建产物已从归档中清理，需要时按 README 重新生成。

## 状态

- 30 tests / 0 failures（归档前最后一次验证）；
- 参数落地 338/538；XR 明确不推进；
- Pixel 基线为人工采集，不是 CI 自动化；采集脚本仍是草稿。

详见 `PROJECT_STATUS.md`。