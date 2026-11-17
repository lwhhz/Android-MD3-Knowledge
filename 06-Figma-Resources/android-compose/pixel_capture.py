#!/usr/bin/env python3
"""Capture regression screenshots for the M3 Kit demo.

Usage: python pixel_capture.py [output_dir]
Requires adb + one device (override ANDROID_SERIAL).
Clears app state, then scrolls to each section title via uiautomator.
Static sections only; date dialog is captured manually if needed.
"""
import os, re, subprocess, sys, time
SERIAL = os.environ.get("ANDROID_SERIAL", "e91fb0f")
OUT = sys.argv[1] if len(sys.argv) > 1 else "pixel-capture-current"
TARGETS = [
    ("M3ConnectedButtonGroup", "connected-button-group.png"),
    ("M3Carousel", "carousel.png"),
    ("M3PlainTooltip", "p2-primitives.png"),
    ("M3Keyboard", "p2-keyboard-grid.png"),
    ("RTL smoke", "rtl-smoke.png"),
]
def adb(*a, t=8):
    try:
        return subprocess.run(["adb","-s",SERIAL,*a],capture_output=True,check=False,timeout=t).stdout
    except subprocess.TimeoutExpired:
        return b""
def xml():
    adb("shell","uiautomator","dump","/sdcard/pc.xml",t=6)
    return adb("shell","cat","/sdcard/pc.xml",t=6).decode("utf-8","replace")
def pos(text, s):
    for m in re.finditer(r'<node[^>]*text="([^"]*)"[^>]*bounds="\[(\d+),(\d+)\]\[(\d+),(\d+)\]"', s):
        if m.group(1) == text:
            return tuple(map(int,m.groups()[1:]))
    return None
def capture(name):
    os.makedirs(OUT, exist_ok=True)
    data = adb("exec-out","screencap","-p",t=15)
    open(os.path.join(OUT,name),"wb").write(data)
    print("captured", name, len(data))
def main():
    adb("shell","pm","clear","com.example.m3appbar",t=15)
    adb("shell","am","start","-n","com.example.m3appbar/.MainActivity",t=15)
    time.sleep(4)
    for title, name in TARGETS:
        found = False
        for _ in range(26):
            p = pos(title, xml())
            if p:
                found = True
                break
            adb("shell","input","keyevent","93")
            time.sleep(0.25)
        if not found:
            print("MISS", title)
            continue
        time.sleep(0.4)
        capture(name)
    print("done")
main()
